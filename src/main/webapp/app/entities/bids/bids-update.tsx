import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IAddress } from 'app/shared/model/address.model';
import { getEntities as getAddresses } from 'app/entities/address/address.reducer';
import { IBiddingDetails } from 'app/shared/model/bidding-details.model';
import { getEntities as getBiddingDetails } from 'app/entities/bidding-details/bidding-details.reducer';
import { IActor } from 'app/shared/model/actor.model';
import { getEntities as getActors } from 'app/entities/actor/actor.reducer';
import { getEntity, updateEntity, createEntity, reset } from './bids.reducer';
import { IBids } from 'app/shared/model/bids.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { BidStatus } from 'app/shared/model/enumerations/bid-status.model';

export const BidsUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const addresses = useAppSelector(state => state.address.entities);
  const biddingDetails = useAppSelector(state => state.biddingDetails.entities);
  const actors = useAppSelector(state => state.actor.entities);
  const bidsEntity = useAppSelector(state => state.bids.entity);
  const loading = useAppSelector(state => state.bids.loading);
  const updating = useAppSelector(state => state.bids.updating);
  const updateSuccess = useAppSelector(state => state.bids.updateSuccess);
  const bidStatusValues = Object.keys(BidStatus);
  const handleClose = () => {
    props.history.push('/bids');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getAddresses({}));
    dispatch(getBiddingDetails({}));
    dispatch(getActors({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...bidsEntity,
      ...values,
      buyerAddress: addresses.find(it => it.id.toString() === values.buyerAddress.toString()),
      biddingDetails: biddingDetails.find(it => it.id.toString() === values.biddingDetails.toString()),
      buyer: actors.find(it => it.id.toString() === values.buyer.toString()),
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
          bidStatus: 'New',
          ...bidsEntity,
          buyerAddress: bidsEntity?.buyerAddress?.id,
          biddingDetails: bidsEntity?.biddingDetails?.id,
          buyer: bidsEntity?.buyer?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="mullyaApp.bids.home.createOrEditLabel" data-cy="BidsCreateUpdateHeading">
            <Translate contentKey="mullyaApp.bids.home.createOrEditLabel">Create or edit a Bids</Translate>
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
                  id="bids-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('mullyaApp.bids.bidPrice')}
                id="bids-bidPrice"
                name="bidPrice"
                data-cy="bidPrice"
                type="text"
              />
              <ValidatedField
                label={translate('mullyaApp.bids.quantityKg')}
                id="bids-quantityKg"
                name="quantityKg"
                data-cy="quantityKg"
                type="text"
              />
              <ValidatedField
                label={translate('mullyaApp.bids.bidStatus')}
                id="bids-bidStatus"
                name="bidStatus"
                data-cy="bidStatus"
                type="select"
              >
                {bidStatusValues.map(bidStatus => (
                  <option value={bidStatus} key={bidStatus}>
                    {translate('mullyaApp.BidStatus.' + bidStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('mullyaApp.bids.createdOn')}
                id="bids-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="date"
              />
              <ValidatedField
                label={translate('mullyaApp.bids.createdBy')}
                id="bids-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('mullyaApp.bids.updatedOn')}
                id="bids-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <ValidatedField
                label={translate('mullyaApp.bids.updatedBy')}
                id="bids-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                id="bids-buyerAddress"
                name="buyerAddress"
                data-cy="buyerAddress"
                label={translate('mullyaApp.bids.buyerAddress')}
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
              <ValidatedField
                id="bids-biddingDetails"
                name="biddingDetails"
                data-cy="biddingDetails"
                label={translate('mullyaApp.bids.biddingDetails')}
                type="select"
              >
                <option value="" key="0" />
                {biddingDetails
                  ? biddingDetails.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="bids-buyer" name="buyer" data-cy="buyer" label={translate('mullyaApp.bids.buyer')} type="select">
                <option value="" key="0" />
                {actors
                  ? actors.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bids" replace color="info">
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

export default BidsUpdate;