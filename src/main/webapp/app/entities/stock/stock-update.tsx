import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IAddress } from 'app/shared/model/address.model';
import { getEntities as getAddresses } from 'app/entities/address/address.reducer';
import { IActor } from 'app/shared/model/actor.model';
import { getEntities as getActors } from 'app/entities/actor/actor.reducer';
import { ICatalogue } from 'app/shared/model/catalogue.model';
import { getEntities as getCatalogues } from 'app/entities/catalogue/catalogue.reducer';
import { getEntity, updateEntity, createEntity, reset } from './stock.reducer';
import { IStock } from 'app/shared/model/stock.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { StockStatus } from 'app/shared/model/enumerations/stock-status.model';

export const StockUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const addresses = useAppSelector(state => state.address.entities);
  const actors = useAppSelector(state => state.actor.entities);
  const catalogues = useAppSelector(state => state.catalogue.entities);
  const stockEntity = useAppSelector(state => state.stock.entity);
  const loading = useAppSelector(state => state.stock.loading);
  const updating = useAppSelector(state => state.stock.updating);
  const updateSuccess = useAppSelector(state => state.stock.updateSuccess);
  const stockStatusValues = Object.keys(StockStatus);
  const handleClose = () => {
    props.history.push('/stock');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getAddresses({}));
    dispatch(getActors({}));
    dispatch(getCatalogues({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...stockEntity,
      ...values,
      farmerAddress: addresses.find(it => it.id.toString() === values.farmerAddress.toString()),
      farmer: actors.find(it => it.id.toString() === values.farmer.toString()),
      category: catalogues.find(it => it.id.toString() === values.category.toString()),
      variant: catalogues.find(it => it.id.toString() === values.variant.toString()),
      subVariant: catalogues.find(it => it.id.toString() === values.subVariant.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          stockStatus: 'New',
          ...stockEntity,
          farmerAddress: stockEntity?.farmerAddress?.id,
          farmer: stockEntity?.farmer?.id,
          category: stockEntity?.category?.id,
          variant: stockEntity?.variant?.id,
          subVariant: stockEntity?.subVariant?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="mulyaaApp.stock.home.createOrEditLabel" data-cy="StockCreateUpdateHeading">
            <Translate contentKey="mulyaaApp.stock.home.createOrEditLabel">Create or edit a Stock</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="stock-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('mulyaaApp.stock.minPrice')}
                id="stock-minPrice"
                name="minPrice"
                data-cy="minPrice"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.stock.maxPrice')}
                id="stock-maxPrice"
                name="maxPrice"
                data-cy="maxPrice"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.stock.quantityKg')}
                id="stock-quantityKg"
                name="quantityKg"
                data-cy="quantityKg"
                type="text"
              />
              <ValidatedField label={translate('mulyaaApp.stock.expiry')} id="stock-expiry" name="expiry" data-cy="expiry" type="text" />
              <ValidatedField
                label={translate('mulyaaApp.stock.avialableFrom')}
                id="stock-avialableFrom"
                name="avialableFrom"
                data-cy="avialableFrom"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.stock.description')}
                id="stock-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.stock.stockStatus')}
                id="stock-stockStatus"
                name="stockStatus"
                data-cy="stockStatus"
                type="select"
              >
                {stockStatusValues.map(stockStatus => (
                  <option value={stockStatus} key={stockStatus}>
                    {translate('mulyaaApp.StockStatus.' + stockStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('mulyaaApp.stock.isOpenForBidding')}
                id="stock-isOpenForBidding"
                name="isOpenForBidding"
                data-cy="isOpenForBidding"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('mulyaaApp.stock.createdOn')}
                id="stock-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="date"
              />
              <ValidatedField
                label={translate('mulyaaApp.stock.createdBy')}
                id="stock-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.stock.updatedOn')}
                id="stock-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <ValidatedField
                label={translate('mulyaaApp.stock.updatedBy')}
                id="stock-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                id="stock-farmerAddress"
                name="farmerAddress"
                data-cy="farmerAddress"
                label={translate('mulyaaApp.stock.farmerAddress')}
                type="select"
              >
                <option value="" key="0" />
                {addresses
                  ? addresses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="stock-farmer" name="farmer" data-cy="farmer" label={translate('mulyaaApp.stock.farmer')} type="select">
                <option value="" key="0" />
                {actors
                  ? actors.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="stock-category"
                name="category"
                data-cy="category"
                label={translate('mulyaaApp.stock.category')}
                type="select"
              >
                <option value="" key="0" />
                {catalogues
                  ? catalogues.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="stock-variant"
                name="variant"
                data-cy="variant"
                label={translate('mulyaaApp.stock.variant')}
                type="select"
              >
                <option value="" key="0" />
                {catalogues
                  ? catalogues.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="stock-subVariant"
                name="subVariant"
                data-cy="subVariant"
                label={translate('mulyaaApp.stock.subVariant')}
                type="select"
              >
                <option value="" key="0" />
                {catalogues
                  ? catalogues.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/stock" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default StockUpdate;
