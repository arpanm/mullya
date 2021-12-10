import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IStock } from 'app/shared/model/stock.model';
import { getEntities as getStocks } from 'app/entities/stock/stock.reducer';
import { getEntity, updateEntity, createEntity, reset } from './bidding-details.reducer';
import { IBiddingDetails } from 'app/shared/model/bidding-details.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { BiddingStatus } from 'app/shared/model/enumerations/bidding-status.model';

export const BiddingDetailsUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const stocks = useAppSelector(state => state.stock.entities);
  const biddingDetailsEntity = useAppSelector(state => state.biddingDetails.entity);
  const loading = useAppSelector(state => state.biddingDetails.loading);
  const updating = useAppSelector(state => state.biddingDetails.updating);
  const updateSuccess = useAppSelector(state => state.biddingDetails.updateSuccess);
  const biddingStatusValues = Object.keys(BiddingStatus);
  const handleClose = () => {
    props.history.push('/bidding-details');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getStocks({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...biddingDetailsEntity,
      ...values,
      stock: stocks.find(it => it.id.toString() === values.stock.toString()),
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
          biddingStatus: 'New',
          ...biddingDetailsEntity,
          stock: biddingDetailsEntity?.stock?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="mulyaaApp.biddingDetails.home.createOrEditLabel" data-cy="BiddingDetailsCreateUpdateHeading">
            <Translate contentKey="mulyaaApp.biddingDetails.home.createOrEditLabel">Create or edit a BiddingDetails</Translate>
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
                  id="bidding-details-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('mulyaaApp.biddingDetails.startDate')}
                id="bidding-details-startDate"
                name="startDate"
                data-cy="startDate"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.biddingDetails.endDate')}
                id="bidding-details-endDate"
                name="endDate"
                data-cy="endDate"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.biddingDetails.biddingStatus')}
                id="bidding-details-biddingStatus"
                name="biddingStatus"
                data-cy="biddingStatus"
                type="select"
              >
                {biddingStatusValues.map(biddingStatus => (
                  <option value={biddingStatus} key={biddingStatus}>
                    {translate('mulyaaApp.BiddingStatus.' + biddingStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('mulyaaApp.biddingDetails.minPrice')}
                id="bidding-details-minPrice"
                name="minPrice"
                data-cy="minPrice"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.biddingDetails.maxPrice')}
                id="bidding-details-maxPrice"
                name="maxPrice"
                data-cy="maxPrice"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.biddingDetails.minQuantityKg')}
                id="bidding-details-minQuantityKg"
                name="minQuantityKg"
                data-cy="minQuantityKg"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.biddingDetails.maxQuantityKg')}
                id="bidding-details-maxQuantityKg"
                name="maxQuantityKg"
                data-cy="maxQuantityKg"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.biddingDetails.isActive')}
                id="bidding-details-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('mulyaaApp.biddingDetails.createdOn')}
                id="bidding-details-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="date"
              />
              <ValidatedField
                label={translate('mulyaaApp.biddingDetails.createdBy')}
                id="bidding-details-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.biddingDetails.updatedOn')}
                id="bidding-details-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <ValidatedField
                label={translate('mulyaaApp.biddingDetails.updatedBy')}
                id="bidding-details-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                id="bidding-details-stock"
                name="stock"
                data-cy="stock"
                label={translate('mulyaaApp.biddingDetails.stock')}
                type="select"
              >
                <option value="" key="0" />
                {stocks
                  ? stocks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bidding-details" replace color="info">
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

export default BiddingDetailsUpdate;
