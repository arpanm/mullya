import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './bids.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BidsDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const bidsEntity = useAppSelector(state => state.bids.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bidsDetailsHeading">
          <Translate contentKey="mullyaApp.bids.detail.title">Bids</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bidsEntity.id}</dd>
          <dt>
            <span id="bidPrice">
              <Translate contentKey="mullyaApp.bids.bidPrice">Bid Price</Translate>
            </span>
          </dt>
          <dd>{bidsEntity.bidPrice}</dd>
          <dt>
            <span id="quantityKg">
              <Translate contentKey="mullyaApp.bids.quantityKg">Quantity Kg</Translate>
            </span>
          </dt>
          <dd>{bidsEntity.quantityKg}</dd>
          <dt>
            <span id="bidStatus">
              <Translate contentKey="mullyaApp.bids.bidStatus">Bid Status</Translate>
            </span>
          </dt>
          <dd>{bidsEntity.bidStatus}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="mullyaApp.bids.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>{bidsEntity.createdOn ? <TextFormat value={bidsEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="mullyaApp.bids.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{bidsEntity.createdBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="mullyaApp.bids.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>{bidsEntity.updatedOn ? <TextFormat value={bidsEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="mullyaApp.bids.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{bidsEntity.updatedBy}</dd>
          <dt>
            <Translate contentKey="mullyaApp.bids.buyerAddress">Buyer Address</Translate>
          </dt>
          <dd>{bidsEntity.buyerAddress ? bidsEntity.buyerAddress.id : ''}</dd>
          <dt>
            <Translate contentKey="mullyaApp.bids.biddingDetails">Bidding Details</Translate>
          </dt>
          <dd>{bidsEntity.biddingDetails ? bidsEntity.biddingDetails.id : ''}</dd>
          <dt>
            <Translate contentKey="mullyaApp.bids.buyer">Buyer</Translate>
          </dt>
          <dd>{bidsEntity.buyer ? bidsEntity.buyer.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/bids" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bids/${bidsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BidsDetail;
